import {useState} from "react";
import Button from "../../../Button/Button";
import Input from "../../../Input/Input";
import Spacer from "../../../Spacer/Spacer";
import "./AdminLoginPage.css";

const AdminLoginPage = () => {
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");

    const authorizeAdmin = () => {
        const credentials = {
            login,
            password
        }

        console.log(credentials);
    }

    return (
        <div className="AdminLoginPage">
            <div className="container">
                <div className="row">
                    <div className="window-height s-vflex-center">
                        <div className="s-hflex-center">
                            <div className="form s-vflex">
                                <Input
                                    placeholder="Логін"
                                    inputType="secondary"
                                    value={login}
                                    onChange={event => setLogin(event.target.value)} />
                                <Spacer height={10} />
                                <Input
                                    className="browser-default"
                                    type="password"
                                    placeholder="Пароль"
                                    inputType="secondary"
                                    value={password}
                                    onChange={event => setPassword(event.target.value)} />
                                <Spacer height={20} />
                                <div className="s-hflex-center">
                                    <Button isFilled={true} type="secondary" onClick={authorizeAdmin}>
                                        Увійти
                                    </Button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AdminLoginPage;
