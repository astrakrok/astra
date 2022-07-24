import TextHeader from "../TextHeader/TextHeader";
import {exams} from "../../data/mock/exams";
import {Exams} from "../Exams/Exams";

export const ExamSection = () => {
    return (
        <div className="container">
            <div className="row s-vflex">
                <TextHeader text="Іспити" />
                <Exams exams={exams} />
            </div>
        </div>
    );
}
