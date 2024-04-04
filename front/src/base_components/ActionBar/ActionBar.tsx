import { Link, Outlet } from "react-router-dom";
import styles from "./ActionBar.module.css"

interface Props {}

export function ActionBar(props: Props) {
    return (
        <>
            <header className={styles.header}>
                <div className={styles.header_container}>
                    <div className={styles.header_left}>
                        <a id={styles.img_container} href="https://urfu.ru/">
                            <img id={styles.image_link_container} src={process.env.PUBLIC_URL + "/logo_urfu.svg"} alt="Ufru logo" />
                        </a>
                        <nav>
                            <Link className={styles.link} to="/administrator/educational_program">Образовательные программы</Link>
                            <Link className={styles.link} to="/administrator/courses_and_modules">Курсы и модули</Link>
                            <Link className={styles.link} to="/administrator/statistics">Статистика</Link>
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
