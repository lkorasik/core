export class Callback<T> {
    success?: (x: T) => void
    error?: (x: any) => void

    constructor(success: (x: T) => void, error?: (x: any) => void) {
        this.success = success;
        this.error = error;
    }
}