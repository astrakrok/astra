import {useEffect, useState} from "react";
import {getAll} from "../../../service/exam.service";
import {getDescriptions} from "../../../service/testing.service";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";

const withExamsAndDescriptions = (
    Component,
    loaderSize = "big"
) => {
    return props => {
        const [exams, setExams] = useState(null);
        const [descriptions, setDescriptions] = useState(null);

        useEffect(() => {
            const fetchExams = async () => {
                const result = await getAll();
                setExams(result);
            }

            const fetchDescriptions = async () => {
                const result = await getDescriptions();
                setDescriptions(result);
            }

            fetchExams();
            fetchDescriptions();
        }, []);

        return (
            <LoaderBoundary size={loaderSize} condition={exams == null || descriptions == null}>
                <Component {...props} exams={exams} descriptions={descriptions}/>
            </LoaderBoundary>
        );
    };
}

export default withExamsAndDescriptions;
