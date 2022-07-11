import CardTemplate from "../../CardTemplate/CardTemplate";
import withTitle from "../../hoc/withTitle/withTitle";
import LoginContent from "./LoginContent/LoginContent";
import LoginHeading from "./LoginHeading/LoginHeading";
import "./LoginPage.css";

const LoginPage = () => {
    return (
        <div className="container LoginPage">
            <div className="row">
                <CardTemplate heading={<LoginHeading />} content={<LoginContent />} />
            </div>
        </div>
    );
}

export default withTitle(LoginPage, "Увійти");
