import { Button } from "../../Button/Button";
import { Input } from "../../Input/Input";
import { Spacer } from "../../Spacer/Spacer";
import "./LoginPage.css";

export const LoginPage = () => {
    document.title = "Увійти";

    return (
        <div className="container LoginPage">
            <div className="row">
                <div className="s-vflex z-depth-2 authentication">
                    <div className="s-vflex heading">
                        <p className="title">Увійти</p>
                        <p className="subtitle">Раді бачити знову</p>
                    </div>
                    <div className="content s-hflex">
                        <div className="credentials equal-flex s-hflex-center">
                            <div className="form">
                                <Input placeholder="E-mail або username" />
                                <Input type="password" className="browser-default" placeholder="Пароль" />
                                <div className="button">
                                    <Button>
                                        Увійти
                                    </Button>
                                </div>
                            </div>
                        </div>
                        <div className="v-separator" />
                        <div className="social equal-flex s-hflex-center">
                            <div className="s-vflex-center full-height">
                                <Button className="full-width" isFilled={true}>Продовжити з Google</Button>
                                <Spacer size="25" />
                                <Button className="full-width" isFilled={true}>Продовжити з Facebook</Button>
                                <Spacer size="25" />
                                <Button className="full-width" isFilled={true}>Продовжити з Instagram</Button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
