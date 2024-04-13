import { Link, Outlet } from "react-router-dom";
import styles from "./ActionBar.module.css"

interface Props {
    item: LinkItem[]
}

interface LinkItem {
    label: string
    link: string
}

export function ActionBar(props: Props) {
    const renderItems = () => {
        return props.item.map(x => <Link className={styles.link} to={x.link}>{x.label}</Link>)
    }

    return (
        <>
            <header className={styles.header}>
                <div className={styles.header_container}>
                    <div className={styles.header_left}>
                        <a id={styles.img_container} href="https://urfu.ru/">
                            <img id={styles.image_link_container} src={process.env.PUBLIC_URL + "/logo_urfu.svg"} alt="Ufru logo" />
                        </a>
                        <nav>
                            {renderItems()}
                        </nav>
                    </div>
                    <div className={styles.header_right}>
                        <button className={styles.search_button}>
                            <img src={process.env.PUBLIC_URL + "/search_icon.svg"} alt={"Search icon"} />
                        </button>
                    </div>
                </div>
            </header>

            <div>
                <Outlet />
            </div>
        </>
    )
}
