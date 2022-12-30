import {Link} from "react-router-dom";
import {page} from "../../constant/page";
import ProgressBar from "../ProgressBar/ProgressBar";
import Tooltipped from "../Tooltipped/Tooltipped";
import corona from "./corona.png";
import "./ExamStatisticItem.css";

const ExamStatisticItem = ({statistic}) => {
    const last = Math.round(statistic.lastCorrectCount * 100.0 / statistic.lastTotalCount);
    const best = Math.round(statistic.bestCorrectCount * 100.0 / statistic.bestTotalCount);

    const getSearch = () => `?testingId=${statistic.testing.id}&mode=examination`;

    const getNavigation = () => ({
        pathname: page.testing,
        search: getSearch()
    });

    return (
        <div className={`ExamStatisticItem ${statistic.isSuccess ? "success" : "failure"} s-hflex`}>
            <ProgressBar title={`${statistic.testing.exam.title} (${statistic.testing.specialization.title})`} progress={last}>
                <div className="percentage s-hflex">
                    <div className="last s-vflex-center">
                        {last} %
                    </div>
                    <div className="s-vflex-center">
                        /
                    </div>
                    <div className="best s-vflex-center">
                    {best} %
                    </div>
                    <img src={corona} alt="score" className="corona full-height" />
                </div>
            </ProgressBar>
            <div className="s-vflex-center arrow">
                <Tooltipped position="top" tooltip="Пройти ще раз">
                    <Link to={getNavigation()} className="s-vflex-center">
                        <i className="material-icons">arrow_forward</i>
                    </Link>
                </Tooltipped>
            </div>
        </div>
    );
}

export default ExamStatisticItem;
