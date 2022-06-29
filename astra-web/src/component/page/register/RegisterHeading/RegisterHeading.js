import {app} from "../../../../constant/app";
import "./RegisterHeading.css";

export const RegisterHeading = () => {
    return (
        <div className="RegisterHeading heading">
            <p className="title">Зареєструватися</p>
            <p className="subtitle">Вітаємо у {app.shortName.toUpperCase()}</p>
        </div>
    );
}
