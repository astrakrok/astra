import Button from "../../../Button/Button";
import { Input } from "../../../Input/Input";
import { ResponsiveColumns } from "../../../ResponsiveColumns/ResponsiveColumns";
import Spacer from "../../../Spacer/Spacer";
import "./LoginContent.css";

export const LoginContent = () => {
    const getFirstColumn = () => {
        return (
            <div className="optimal login-form">
                <Input placeholder="E-mail" />
                <Input type="password" className="browser-default" placeholder="Пароль" />
                <div className="button center">
                    <Button>
                        Увійти
                    </Button>
                </div>
            </div>
        );
    };

    const getSecondColumn = () => {
        return (
            <div className="s-vflex-center full-height">
                <Button className="full-width" isFilled={true}>Продовжити з Google</Button>
                <Spacer height="25" />
                <Button className="full-width" isFilled={true}>Продовжити з Facebook</Button>
                <Spacer height="25" />
                <Button className="full-width" isFilled={true}>Продовжити з Instagram</Button>
            </div>
        );
    };

    return (
        <ResponsiveColumns
            firstColumn={getFirstColumn()}
            secondColumn={getSecondColumn()}
            isSeparated={true} />
    );
}
