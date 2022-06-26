import { Star } from "../Star/Star";
import "./Exams.css";

export const Exams = ({exams}) => {
    return (
        <div className="s-hflex-center wrap-flex Exams">
            {
                exams.map((exam, index) => <div key={index} className="center exam-item"><Star title={exam.title} /></div>)
            }
        </div>
    );
}
