import CardTemplate from "../../CardTemplate/CardTemplate";
import withTitle from "../../hoc/withTitle/withTitle";
import RegisterContent from "./RegisterContent/RegisterContent";
import RegisterHeading from "./RegisterHeading/RegisterHeading";
import "./RegisterPage.css";

const RegisterPage = () => {

    return (
        <div className="container">
            <div className="row">
                <CardTemplate heading={<RegisterHeading />} content={<RegisterContent />}/>
            </div>
        </div>
    );
}

export default withTitle(RegisterPage, "Зареєструватися");
