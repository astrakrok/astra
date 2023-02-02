import InfoHeader from "../InfoHeader/InfoHeader";
import ProgressBar from "../ProgressBar/ProgressBar";
import Spacer from "../Spacer/Spacer";
import "./StepStatisticItem.css";

const StepSatisticItem = ({
    statistic
}) => {
    const calculateSubjectProgress = statistic => (
        statistic.totalCount === 0 ? 0 : Math.round(statistic.correctCount * 100.0 / statistic.totalCount)
    );

    const renderSubjectItem = subject => (
        <div key={subject.id} className="subject">
            <ProgressBar className={subject.statistic.totalCount === 0 ? "disabled" : ""} title={subject.title} progress={calculateSubjectProgress(subject.statistic)}>
                <div className="percentage s-vflex-center">
                    {subject.statistic.correctCount} / {subject.statistic.totalCount}
                </div>
            </ProgressBar>
        </div>
    );

    const renderSpecializationItem = specialization => (
        <div key={specialization.id} className="s-vflex">
            <InfoHeader>{specialization.title}</InfoHeader>
            {
                specialization.subjects.map(renderSubjectItem)
            }
            <Spacer height={30} />
        </div>
    );

    return (
        <div className="StepStatisticItem s-vflex-center">
            <Spacer height={15} />
            <div className="step s-hflex-center">
                <div className="s-vflex-center"><i className="material-icons small">star</i></div>
                <div className="title">{statistic.title}</div>
                <div className="s-vflex-center"><i className="material-icons small">star</i></div>
            </div>
            {
                statistic.specializations.map(renderSpecializationItem)
            }
        </div>
    );
}

export default StepSatisticItem;
