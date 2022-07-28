import {DonutChart} from "react-circle-chart";
import InfoText from "../InfoText/InfoText";
import "./TestingStatistic.css";

const TestingStatistic = ({statistic}) => {
    const percent = Math.round(statistic.correctCount / statistic.total * 100);

    return (
        <div className="TestingStatistic s-vflex">
            <div className="s-hflex-center">
                <InfoText>
                    Ваш результат
                </InfoText>
            </div>
            <div className="s-hflex-center">
                <DonutChart trackWidth="lg" size="sm" items={[{
                    value: percent,
                    label: "Правильність"
                }]} />
            </div>
        </div>
    );
}

export default TestingStatistic;
