import { ApiError } from "./ApiError";

interface ParamsMap {
    [key: string]: null | undefined | number | string | any[] | boolean;
}

export class ApiBase {
    public getAdditionalHeaders: () => RequestInit;
    public prefix: string;
    public getToken: () => string | null;

    public constructor(prefix: string, getToken: () => string | null) {
        this.prefix = prefix;
        this.getToken = getToken;
        this.getAdditionalHeaders = () => ({
            headers: {
                "Cache-Control": "no-cache, no-store",
                "Pragma": "no-cache",
                "Expires": "0",
                "Content-Type": "application/json",
                "Authorization": "Bearer " + this.getToken(),
                "Accept": "application/json",
            },
            "credentials": "same-origin",
        });
    }

    public async checkStatus(response: Response): Promise<void> {
        if (!(response.status >= 200 && response.status < 300)) {
            const errorText = await response.text();
            let serverResponse;
            try {
                serverResponse = JSON.parse(errorText);
            } catch (e) {
                serverResponse = null;
            }
            if (serverResponse != null) {
                throw new ApiError(
                    errorText,
                    serverResponse.reason,
                    response.status,
                    serverResponse.type,
                    serverResponse.stackTrace,
                    serverResponse.innerErrors
                );
            }
            throw new ApiError(errorText, "Unexpected error format", response.status);
        }
    }

    public async post(
        url: string,
        params: ParamsMap,
        body: null | undefined | {} | any[]
    ): Promise<any> {
        const response = await fetch(this.prefix + url + this.createQueryString(params), {
            ...this.getAdditionalHeaders(),
            method: "POST",
            body: JSON.stringify(body),
        });
        await this.checkStatus(response);
        const textResult = await response.text();
        if (textResult !== "") {
            return JSON.parse(textResult);
        }
        return undefined;
    }

    public async put(url: string, params: ParamsMap, body: null | undefined | {} | any[]): Promise<any> {
        const response = await fetch(this.prefix + url + this.createQueryString(params), {
            ...this.getAdditionalHeaders(),
            method: "PUT",
            body: JSON.stringify(body),
        });
        await this.checkStatus(response);
        const textResult = await response.text();
        if (textResult !== "") {
            return JSON.parse(textResult);
        }
        return undefined;
    }

    public async putBinary(url: string, params: ParamsMap, body: Blob): Promise<any> {
        const response = await fetch(this.prefix + url + this.createQueryString(params), {
            ...this.getAdditionalHeaders(),
            method: "PUT",
            body: body,
        });
        await this.checkStatus(response);
        const textResult = await response.text();
        if (textResult !== "") {
            return JSON.parse(textResult);
        }
        return undefined;
    }

    public createQueryString(params: null | undefined | ParamsMap): string {
        if (params == null) {
            return "";
        }
        const params2 = params;
        let result = Object.keys(params)
            .map(key => {
                const value = params2[key];
                if (typeof value === "string") {
                    return encodeURIComponent(key) + "=" + encodeURIComponent(value);
                }
                if (typeof value === "number" || typeof value === "boolean") {
                    return encodeURIComponent(key) + "=" + encodeURIComponent(value.toString());
                }
                if (Array.isArray(value)) {
                    return value
                        .map(item => {
                            if (typeof item === "string") {
                                return encodeURIComponent(key) + "=" + encodeURIComponent(item);
                            }
                            if (typeof item === "number") {
                                return encodeURIComponent(key) + "=" + encodeURIComponent(item.toString());
                            }
                            return null;
                        })
                        .filter(x => x !== null)
                        .join("&");
                }
                return null;
            })
            .filter(x => x !== null)
            .join("&");
        result = result ? "?" + result : "";
        return result;
    }

    public async get(url: string, params?: ParamsMap, _body?: any): Promise<any> {
        const response = await fetch(this.prefix + url + this.createQueryString(params), {
            ...this.getAdditionalHeaders(),
            method: "GET",
        });
        await this.checkStatus(response);
        const result = await response.text();
        if (result) {
            return JSON.parse(result);
        }
        return null;
    }

    public async head(url: string, params?: ParamsMap): Promise<any> {
        const response = await fetch(this.prefix + url + this.createQueryString(params), {
            ...this.getAdditionalHeaders(),
            method: "HEAD",
        });
        return response.status >= 200 && response.status < 300;
    }

    public async download(url: string, params?: ParamsMap, _body?: any): Promise<any> {
        const headResult = await this.head(url, params);
        if (headResult) {
            window.location.href = this.prefix + url + this.createQueryString(params);
        } else {
            return await this.get(url, params);
        }
    }

    public async downloadFile(url: string, filename: string, params?: ParamsMap): Promise<void> {
        const response = await fetch(this.prefix + url + this.createQueryString(params), {
            ...this.getAdditionalHeaders(),
            headers: {
                ...this.getAdditionalHeaders().headers,
                "Content-Type": "application/octet-stream",
                "Accept": "application/octet-stream",
            },
            method: "GET",
        });
        const fileUrl = window.URL.createObjectURL(await response.blob());
        const linkElement = document.createElement('a');
        linkElement.id = "fileLink";
        linkElement.href = fileUrl;
        linkElement.download = filename;
        linkElement.click();
        linkElement.remove();
    }

    public async delete(url: string, params?: ParamsMap, body?: {}): Promise<any> {
        const response = await fetch(this.prefix + url + this.createQueryString(params), {
            ...this.getAdditionalHeaders(),
            method: "DELETE",
            body: JSON.stringify(body),
        });
        await this.checkStatus(response);
        const textResult = await response.text();
        if (textResult !== "") {
            return JSON.parse(textResult);
        }
        return undefined;
    }
}
