import { InfoText } from "../InfoText/InfoText";
import { TextHeader } from "../TextHeader/TextHeader";
import study from "./study.png";
import "./StudySection.css";

export const StudySection = () => {
    return (
        <div className="container StudySection">
            <div className="row">
                <TextHeader text="Навчання" />
                <div className="s-hflex">
                    <div className="equal-flex s-vflex-center">
                        <img src={study} alt="study" />
                    </div>
                    <div className="equal-flex s-vflex-center content">
                        <InfoText>Lorem ipsum dolor sit amet, consectetur adipiscing elit ut aliquam</InfoText>
                    </div>
                </div>
            </div>
        </div>
    );
}