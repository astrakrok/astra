import {CardTemplate} from "../../CardTemplate/CardTemplate";
import { RegisterContent } from "./RegisterContent/RegisterContent";
import { RegisterHeading } from "./RegisterHeading/RegisterHeading";
import "./RegisterPage.css";

export const RegisterPage = () => {
    document.title = "Зареєструватися";

    return (
        <div className="container">
            <div className="row">
                <CardTemplate heading={<RegisterHeading />} content={<RegisterContent />} />
            </div>
        </div>
    );
}
