import ResponsiveColumns from "../../ResponsiveColumns/ResponsiveColumns";
import InfoHeader from "../../InfoHeader/InfoHeader";
import withTitle from "../../hoc/withTitle/withTitle";
import ChangeUserInfoForm from "../../form/ChangeUserInfoForm/ChangeUserInfoForm";
import ChangePasswordForm from "../../form/ChangePasswordForm/ChangePasswordForm";
import AuthConsumer from "../../../context/auth/AuthConsumer";
import "./SettingsPage.css";
import withSpecializations from "../../hoc/withSpecializations/withSpecializations";

const SettingsPage = () => {
    const UserInfoForm = withSpecializations(ChangeUserInfoForm);

    const renderUserInfoForm = () => (
        <AuthConsumer>
            {
                ({userData, setUserData}) => (
                    <UserInfoForm user={userData} onSuccess={newUserData => setUserData(newUserData)}/>
                )
            }
        </AuthConsumer>
    );

    return (
        <div className="SettingsPage container">
            <div className="row">
                <div className="s-vflex">
                    <div className="s-vflex-center main-info">
                        <InfoHeader>Про Вас</InfoHeader>
                        <ResponsiveColumns
                            className="columns"
                            isSeparated={true}
                            firstColumn={renderUserInfoForm()}
                            secondColumn={<ChangePasswordForm/>}/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default withTitle(SettingsPage, "Налаштування");
