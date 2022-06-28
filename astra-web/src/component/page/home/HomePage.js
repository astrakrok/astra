import { Entry } from "../../Entry/Entry";
import { ExamSection } from "../../ExamSection/ExamSection";
import { OurTeamSection } from "../../OurTeamSection/OurTeamSection";
import { StudySection } from "../../StudySection/StudySection";

export const HomePage = () => {
    document.title = "Astra";

    return (
        <>
            <Entry />
            <ExamSection />
            <StudySection />
            <OurTeamSection />
        </>
    )
}
