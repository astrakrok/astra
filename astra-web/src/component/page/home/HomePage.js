import { Entry } from "../../Entry/Entry";
import { ExamSection } from "../../ExamSection/ExamSection";
import { Header } from "../../Header/Header";
import { OurTeamSection } from "../../OurTeamSection/OurTeamSection";
import { StudySection } from "../../StudySection/StudySection";
import { Footer } from "../../Footer/Footer";

export const HomePage = () => {
    document.title = "Astra";

    return (
        <>
            <Header />
            <Entry />
            <ExamSection />
            <StudySection />
            <OurTeamSection />
            <Footer />
        </>
    )
}
