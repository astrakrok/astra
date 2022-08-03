import ResponsiveColumns from "../../ResponsiveColumns/ResponsiveColumns";
import InfoHeader from "../../InfoHeader/InfoHeader";
import withTitle from "../../hoc/withTitle/withTitle";
import ChangeUserInfoForm from "../../form/ChangeUserInfoForm/ChangeUserInfoForm";
import ChangePasswordForm from "../../form/ChangePasswordForm/ChangePasswordForm";
import ChangeUserAvatarForm from "../../form/ChangeUserAvatarForm/ChangeUserAvatarForm";
import AuthConsumer from "../../../context/auth/AuthConsumer";
import "./SettingsPage.css";

const SettingsPage = () => {
    const renderUserInfoForm = () => (
        <AuthConsumer>
            {
                ({userData, setUserData}) => (
                    <ChangeUserInfoForm user={userData} onSuccess={newUserData => setUserData(newUserData)} />
                )
            }
        </AuthConsumer>
    );

    return (
        <div className="SettingsPage container">
            <div className="row">
                <div className="s-vflex">
                    <div className="s-vflex">
                        <InfoHeader text="Фото" />
                        <ChangeUserAvatarForm />
                    </div>
                    <div className="s-vflex-center main-info">
                        <InfoHeader text="Про Вас" />
                        <ResponsiveColumns
                            className="columns"
                            isSeparated={true}
                            firstColumn={renderUserInfoForm()}
                            secondColumn={<ChangePasswordForm />} />
                    </div>
                </div>
            </div>
        </div>
    );
}

export default withTitle(SettingsPage, "Налаштування");
