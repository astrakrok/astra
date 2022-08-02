import {page} from "../../../constant/page";
import Button from "../../Button/Button";
import Logo from "../../Logo/Logo";
import Spacer from "../../Spacer/Spacer";
import "./ErrorPage.css";

const ErrorPage = () => {
    return (
        <div className="ErrorPage full-width">
            <div className="window-height s-hflex-center">
                <div className="full-height s-vflex-center center">
                    <div className="s-hflex-center">
                        <Logo />
                    </div>
                    <div className="message">
                        Щось пішло не так. Вибачте за незручності та спробуйте ще раз
                    </div>
                    <div className="s-hflex-center navigation">
                        <Button to={page.home} isFilled={true} type="secondary">
                            <div className="s-hflex">
                                <i className="material-icons">arrow_back</i>
                                <Spacer width={8} />
                                <span className="s-vflex-center uppercase">На головну</span>
                            </div>
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ErrorPage;
