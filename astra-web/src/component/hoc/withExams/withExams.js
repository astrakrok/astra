import {useEffect, useState} from "react";
import {getAll} from "../../../service/exam.service";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";

const withExams = (
    Component,
    loaderSize = "big"
) => {
    return props => {
        const [exams, setExams] = useState(null);

        useEffect(() => {
            const fetchExams = async () => {
                const result = await getAll();
                setExams(result);
            }

            fetchExams();
        }, []);

        return (
            <LoaderBoundary size={loaderSize} condition={exams == null}>
                <Component {...props} exams={exams} />
            </LoaderBoundary>
        );
    };
}

export default withExams;
