import { Link } from "react-router-dom";
import { page } from "../../../constant/page";
import "./SiteNavigation.css";

export const SiteNavigation = () => {
    return (
        <div className="s-vflex-center equal-flex SiteNavigation">
            <div className="wrapper s-hflex-center m-hflex-end">
                <Link to={page.exams} className="link">Іспити</Link>
                <Link to={page.feedback} className="link">Відгуки</Link>
            </div>
        </div>
    );
}
