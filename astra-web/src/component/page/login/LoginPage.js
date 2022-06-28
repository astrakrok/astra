import { CardTemplate } from "../../CardTemplate/CardTemplate";
import { LoginContent } from "./LoginContent/LoginContent";
import { LoginHeading } from "./LoginHeading/LoginHeading";
import "./LoginPage.css";

export const LoginPage = () => {
    document.title = "Увійти";

    return (
        <div className="container LoginPage">
            <div className="row">
                <CardTemplate heading={<LoginHeading />} content={<LoginContent />} />
            </div>
        </div>
    );
}
