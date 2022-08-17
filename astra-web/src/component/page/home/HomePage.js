import {app} from "../../../constant/app";
import withTitle from "../../hoc/withTitle/withTitle";
import Button from "../../Button/Button";
import entry from "./entry.png";
import doubts from "./doubts.png";
import creation from "./creation.png";
import {page} from "../../../constant/page";
import Spacer from "../../Spacer/Spacer";
import "./HomePage.css";

const HomePage = () => {
    document.title = "Astra";

    return (
        <div className="container HomePage">
            <div className="row">
                <div className="s-hflex">
                    <div className="equal-flex">
                        <img className="full-width" src={entry} alt="entry"/>
                    </div>
                    <div className="equal-flex s-vflex-center">
                        <div className="info center">Якщо підготовка до КРОКу - сходи, то AstraKROK стане ліфтом;)</div>
                        <Spacer height={20}/>
                        <div className="start s-hflex-center">
                            <Button to={page.register}>
                                Почати
                            </Button>
                        </div>
                    </div>
                </div>
                <div className="s-hflex">
                    <div className="equal-flex s-vflex-center">
                        <div className="info center">Чи бувало в тебе таке, що розв’язуєш тести, робиш помилку і, от
                            халепа, не розумієш, чому твоя відповідь хибна?
                        </div>
                    </div>
                    <div className="equal-flex">
                        <img className="full-width" src={doubts} alt="doubts"/>
                    </div>
                </div>
                <div className="s-hflex">
                    <div className="equal-flex">
                        <img className="full-width" src={creation} alt="creation"/>
                    </div>
                    <div className="equal-flex s-vflex-center">
                        <div className="info center">
                            У нас таке було. І ми знайшли вихід!
                            Ми пояснюємо запитання, правильні та неправильні варіанти відповідей.
                            <br/>
                            Зробили для себе та поділилися зі світом!
                        </div>
                    </div>
                </div>
                <Spacer height={40}/>
                <div className="join s-hflex-center">
                    <Button to={page.register}>
                        Приєднатися
                    </Button>
                </div>
            </div>
        </div>
    )
}

export default withTitle(HomePage, app.name);
