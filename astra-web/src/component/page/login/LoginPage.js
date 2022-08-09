import CardTemplate from "../../CardTemplate/CardTemplate";
import withTitle from "../../hoc/withTitle/withTitle";
import LoginContent from "./LoginContent/LoginContent";
import LoginHeading from "./LoginHeading/LoginHeading";
import "./LoginPage.css";
import AuthConsumer from "../../../context/auth/AuthConsumer";
import {defaultPictureUrl} from "../../../data/default/user";

const LoginPage = () => {
    const handleLogin = async (user, setUserData) => {
        setUserData({
            ...user,
            pictureUrl: user.pictureUrl || defaultPictureUrl
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
                                        onSuccess={user => handleLogin(user, setUserData)} />} />
                            );
                        }
                    }
                </AuthConsumer>
            </div>
        </div>
    );
}

export default withTitle(LoginPage, "Увійти");
