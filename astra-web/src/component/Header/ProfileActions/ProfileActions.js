import { page } from "../../../constant/page";
import { getUser } from "../../../handler/user.handler";
import Dropdown from "../../Dropdown/Dropdown";
import IconTitle from "../../IconTitle/IconTitle";
import CardTemplate from "../../CardTemplate/CardTemplate";
import "./ProfileActions.css";
import { Link } from "react-router-dom";
import DropdownList from "../../DropdownList/DropdownList";
import Divider from "../../Divider/Divider";

const ProfileActions = () => {
    const user = getUser();

    const getUserInformation = () => {
        return (
            <>
                <span className="username">Андрій Босик</span>
                <span className="email">andrijbosyk@gmail.com</span>
            </>
        );
    };

    const getDropdownOptions = () => {
        return (
            <DropdownList items={[
                <Link to={page.profile}>
                    <IconTitle icon="perm_identity" title="Профіль" />
                </Link>,
                <Link to={page.settings}>
                    <IconTitle icon="settings" title="Налаштування" />
                </Link>,
                <Divider />,
                <Link to={page.logout}>
                    <IconTitle icon="exit_to_app" title="Вийти" />
                </Link>
            ]} />
        );
    }

    const getDropdownTrigger = () => {
        return (
            <img src={user.pictureUrl} alt="avatar" className="full-width full-height" />
        );
    };

    const getDropdownContent = () => {
        return (
            <CardTemplate className="dropdown-card" heading={getUserInformation()} content={getDropdownOptions()} />
        );
    }

    return (
        <div className="s-vflex-center ProfileActions">
            <div className="border50p z-depth-2 avatar-container">
                <Dropdown id="profile" trigger={getDropdownTrigger()} content={getDropdownContent()} />
            </div>
        </div>
    );
}

export default ProfileActions;
