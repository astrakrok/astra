import {page} from "../../../constant/page";
import {getUser, isAdmin} from "../../../handler/user.handler";
import Dropdown from "../../Dropdown/Dropdown";
import IconTitle from "../../IconTitle/IconTitle";
import CardTemplate from "../../CardTemplate/CardTemplate";
import "./ProfileActions.css";
import {Link} from "react-router-dom";
import DropdownList from "../../DropdownList/DropdownList";
import Divider from "../../Divider/Divider";

const ProfileActions = () => {
    const user = getUser();

    const getUserInformation = () => {
        return (
            <>
                <span className="username">{user.name} {user.surname}</span>
                <span className="email">{user.email}</span>
            </>
        );
    };

    const getDropdownOptions = () => {
        const items = [
            isAdmin() ? null : (
                <Link to={page.profile}>
                    <IconTitle icon="perm_identity" title="Профіль" />
                </Link>
            ),
            <Link to={page.settings}>
                <IconTitle icon="settings" title="Налаштування" />
            </Link>,
            <Divider />,
            <Link to={page.logout}>
                <IconTitle icon="exit_to_app" title="Вийти" />
            </Link>
        ].filter(item => item != null);

        return (
            <DropdownList items={items} />
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
