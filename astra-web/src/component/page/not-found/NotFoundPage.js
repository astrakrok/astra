import {page} from "../../../constant/page";
import Button from "../../Button/Button";
import withTitle from "../../hoc/withTitle/withTitle";
import Spacer from "../../Spacer/Spacer";
import "./NotFoundPage.css";

const NotFoundPage = () => {
    return (
        <div className="NotFoundPage full-width">
            <div className="window-height s-hflex-center">
                <div className="full-height s-vflex-center center">
                    <div className="code">404</div>
                    <div className="message">Сторінку не знайдено</div>
                    <div className="s-hflex-center navigation">
                        <Button to={page.home} type="secondary" isFilled={true}>
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

export default withTitle(NotFoundPage, "Сторінку не знайдено");
