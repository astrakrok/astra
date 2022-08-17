import {page} from "../../../constant/page";
import {isAdmin, isSuperAdmin} from "../../../handler/user.handler";
import Dropdown from "../../Dropdown/Dropdown";
import IconTitle from "../../IconTitle/IconTitle";
import CardTemplate from "../../CardTemplate/CardTemplate";
import "./ProfileActions.css";
import {Link} from "react-router-dom";
import DropdownList from "../../DropdownList/DropdownList";
import Divider from "../../Divider/Divider";
import AuthConsumer from "../../../context/auth/AuthConsumer";
import {logout} from "../../../service/auth.service";

const ProfileActions = () => {
    const getUserInformation = () => {
        return (
            <AuthConsumer>
                {
                    ({userData}) => (
                        <>
                            <span className="username">{userData.name} {userData.surname}</span>
                            <span className="email">{userData.email}</span>
                        </>
                    )
                }
            </AuthConsumer>
        );
    };

    const getDropdownOptions = () => {
        const items = [
            <AuthConsumer>
                {
                    ({userData}) => {
                        return isAdmin(userData) ? null : (
                            <Link to={page.profile}>
                                <IconTitle icon="perm_identity" title="Профіль"/>
                            </Link>
                        )
                    }
                }
            </AuthConsumer>,
            <AuthConsumer>
                {
                    ({userData}) => {
                        return isSuperAdmin(userData) ? (
                            <Link to={page.configuration}>
                                <IconTitle icon="perm_data_setting" title="Конфігурація"/>
                            </Link>
                        ) : null
                    }
                }
            </AuthConsumer>,
            <Link to={page.settings}>
                <IconTitle icon="settings" title="Налаштування"/>
            </Link>,
            <Divider/>,
            <AuthConsumer>
                {
                    ({setUserData}) => (
                        <Link to="" onClick={() => logout(setUserData)}>
                            <IconTitle icon="exit_to_app" title="Вийти"/>
                        </Link>
                    )
                }
            </AuthConsumer>
        ].filter(item => item != null);

        return (
            <DropdownList items={items} />
        );
    }

    const getDropdownTrigger = () => {
        return (
            <div className="menu-trigger">
                <i className="material-icons">menu</i>
            </div>
        );
    };

    const getDropdownContent = () => {
        return (
            <CardTemplate className="dropdown-card" heading={getUserInformation()} content={getDropdownOptions()} />
        );
    }

    return (
        <div className="s-vflex-center ProfileActions">
            <div className="border50p avatar-container">
                <Dropdown id="profile" trigger={getDropdownTrigger()} content={getDropdownContent()}/>
            </div>
        </div>
    );
}

export default ProfileActions;
