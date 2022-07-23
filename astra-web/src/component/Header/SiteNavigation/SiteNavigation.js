import {Link} from "react-router-dom";
import {page} from "../../../constant/page";
import {isAdmin} from "../../../handler/user.handler";
import Dropdown from "../../Dropdown/Dropdown";
import DropdownList from "../../DropdownList/DropdownList";
import IconTitle from "../../IconTitle/IconTitle";
import "./SiteNavigation.css";

const SiteNavigation = () => {
    const getAdminDropdownTrigger = () => {
        return (
            <div className="link center">
                Панель керування
            </div>
        );
    }

    const getAdminDropdownContent = () => {
        return (
            <DropdownList items={[
                <Link to={page.admin.exams.all}>
                    <IconTitle icon="timelapse" title="Іспити" />
                </Link>,
                <Link to={page.admin.specializations.all}>
                    <IconTitle icon="folder" title="Спеціалізації" />
                </Link>,
                <Link to={page.admin.subjects.all}>
                    <IconTitle icon="library_books" title="Предмети" />
                </Link>,
                <Link to={page.admin.tests.all}>
                    <IconTitle icon="check" title="Тести" />
                </Link>
            ]} />
        );
    }

    return (
        <div className="s-vflex-center equal-flex SiteNavigation">
            <div className="wrapper s-hflex-center m-hflex-end">
                {
                    isAdmin() ? (
                        <Dropdown id="management" trigger={getAdminDropdownTrigger()} content={getAdminDropdownContent()} />
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
