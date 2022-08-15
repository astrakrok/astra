import CardTemplate from "../../CardTemplate/CardTemplate";
import withSpecializations from "../../hoc/withSpecializations/withSpecializations";
import withTitle from "../../hoc/withTitle/withTitle";
import RegisterContent from "./RegisterContent/RegisterContent";
import RegisterHeading from "./RegisterHeading/RegisterHeading";
import "./RegisterPage.css";

const RegisterPage = () => {
    const RegisterForm = withSpecializations(RegisterContent);

    return (
        <div className="container">
            <div className="row">
                <CardTemplate heading={<RegisterHeading/>} content={<RegisterForm/>}/>
            </div>
        </div>
    );
}

export default withTitle(RegisterPage, "Зареєструватися");
