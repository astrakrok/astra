import { Link } from "react-router-dom";
import { page } from "../../../constant/page";
import { isAdmin } from "../../../handler/user.handler";
import "./SiteNavigation.css";

const SiteNavigation = () => {
    return (
        <div className="s-vflex-center equal-flex SiteNavigation">
            <div className="wrapper s-hflex-center m-hflex-end">
                {
                    isAdmin() ? (
                        <Link to={page.admin.specializations.all} className="link">
                            Панель керування
                        </Link>
                    ) : (
                        <>
                            <Link to={page.exams} className="link">Іспити</Link>
                            <Link to={page.feedback} className="link">Відгуки</Link>
                        </>
                    )
                }
            </div>
        </div>
    );
}

export default SiteNavigation;
