import {page} from "../../../constant/page";
import Button from "../../Button/Button";
import LargeLogo from "../../LargeLogo/LargeLogo";
import Spacer from "../../Spacer/Spacer";
import "./ErrorPage.css";

const ErrorPage = () => {
    return (
        <div className="ErrorPage full-width">
            <div className="window-height s-hflex-center">
                <div className="full-height s-vflex-center center">
                    <div className="s-vflex m-hflex">
                        <div className="s-hflex-center">
                            <LargeLogo />
                        </div>
                        <div className="s-vflex-center error">
                            <div className="title">
                                Щось пішло не так...
                            </div>
                            <div className="message">
                                На жаль, щось пішло не так. Будь ласка, спробуйте зайти трохи пізніше
                            </div>
                            <div className="navigation">
                                <Button to={page.home}>
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
            </div>
        </div>
    );
}

export default ErrorPage;
