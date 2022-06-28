import { Button } from "../../../Button/Button";
import { Input } from "../../../Input/Input";
import {ResponsiveColumns} from "../../../ResponsiveColumns/ResponsiveColumns";
import { Spacer } from "../../../Spacer/Spacer";
import "./RegisterContent.css";

export const RegisterContent = () => {
    const getFirstColumn = () => {
        return (
            <div className="optimal register-form">
                <Input placeholder="Ім'я" />
                <Input placeholder="Прізвище" />
                <Input placeholder="Курс" />
                <Input placeholder="E-mail" />
                <Input type="password" className="browser-default" placeholder="Пароль" />
                <Input type="password" className="browser-default" placeholder="Повторити пароль" />
                <div className="button center">
                    <Button>
                        Зареєструватися
                    </Button>
                </div>
            </div>
        );
    };

    const getSecondColumn = () => {
        return (
            <div className="s-vflex-center full-height">
                <Button className="full-width" isFilled={true}>Продовжити з Google</Button>
                <Spacer size="25" />
                <Button className="full-width" isFilled={true}>Продовжити з Facebook</Button>
                <Spacer size="25" />
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
