import { Button } from "../../../Button/Button";
import { Input } from "../../../Input/Input";
import { Spacer } from "../../../Spacer/Spacer";
import "./LoginContent.css";

export const LoginContent = () => {
    return (
        <div className="LoginContent s-vflex m-hflex">
            <div className="credentials equal-flex s-hflex-center m-no-width">
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
            <div className="v-separator hide-on-small-only" />
            <div className="h-separator hide-on-med-and-up" />
            <div className="social equal-flex s-hflex-center m-no-width">
                <div className="s-vflex-center full-height">
                    <Button className="full-width" isFilled={true}>Продовжити з Google</Button>
                    <Spacer size="25" />
                    <Button className="full-width" isFilled={true}>Продовжити з Facebook</Button>
                    <Spacer size="25" />
                    <Button className="full-width" isFilled={true}>Продовжити з Instagram</Button>
                </div>
            </div>
        </div>
    );
}
