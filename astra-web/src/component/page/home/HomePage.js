import {app} from "../../../constant/app";
import {Entry} from "../../Entry/Entry";
import {ExamSection} from "../../ExamSection/ExamSection";
import withTitle from "../../hoc/withTitle/withTitle";
import {StudySection} from "../../StudySection/StudySection";

const HomePage = () => {
    document.title = "Astra";

    return (
        <>
            <Entry />
            <ExamSection />
            <StudySection />
        </>
    )
}

export default withTitle(HomePage, app.name);
