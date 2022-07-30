import CardTemplate from "../../CardTemplate/CardTemplate";
import withTitle from "../../hoc/withTitle/withTitle";
import LoginContent from "./LoginContent/LoginContent";
import LoginHeading from "./LoginHeading/LoginHeading";
import {userRole} from "../../../constant/user.role";
import "./LoginPage.css";
import AuthConsumer from "../../../context/auth/AuthConsumer";
import {saveJson} from "../../../handler/storage.handler";
import {localStorageKey} from "../../../constant/local.storage.key";

const LoginPage = () => {
    const handleLogin = async (tokenResponse, setUserData) => {
        saveJson(localStorageKey.tokenData, tokenResponse);
        setUserData({
            roles: [userRole.user],
            name: "Andrii",
            surname: "Bosyk",
            email: "example@gmail.com",
            pictureUrl: "/images/avatar-1.png"
        });
    }

    return (
        <div className="container LoginPage">
            <div className="row">
                <AuthConsumer>
                    {
                        ({setUserData}) => {
                            return (
                                <CardTemplate
                                    heading={<LoginHeading />}
                                    content={<LoginContent
                                        onSuccess={tokenResponse => handleLogin(tokenResponse, setUserData)} />} />
                            );
                        }
                    }
                </AuthConsumer>
            </div>
        </div>
    );
}

export default withTitle(LoginPage, "Увійти");
