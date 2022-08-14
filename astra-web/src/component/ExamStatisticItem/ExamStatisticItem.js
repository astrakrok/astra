import {Link} from "react-router-dom";
import {page} from "../../constant/page";
import corona from "./corona.png";
import "./ExamStatisticItem.css";

const ExamStatisticItem = ({statistic}) => {
    const getSearch = () => `?testingId=${statistic.testing.id}&mode=examination`;

    const getNavigation = () => ({
        pathname: page.testing,
        search: getSearch()
    });

    return (
        <div className={`ExamStatisticItem clickable ${statistic.isSuccess ? "success" : "failure"}`}>
            <div className="progress-bar s-vflex-center">
                <Link to={getNavigation()}>
                    <div className="s-hflex content full-width full-height">
                        <div className="title s-vflex-center equal-flex">
                            {statistic.testing.exam.title} ({statistic.testing.specialization.title})
                        </div>
                        <div className="percentage s-hflex">
                            <div className="last s-vflex-center">
                                {statistic.last} %
                            </div>
                            <div className="s-vflex-center">
                                /
                            </div>
                            <div className="best s-vflex-center">
                                {statistic.best} %
                            </div>
                            <img src={corona} alt="score" className="corona full-height" />
                        </div>
                    </div>
                </Link>
                <div className="filler" style={{width: statistic.last + "%"}} />
            </div>
        </div>
    );
}

export default ExamStatisticItem;
