import {useEffect, useState} from "react";
import {getAll as getAllSpecializations} from "../../../service/specialization.service";
import {getAll as getAllExams} from "../../../service/exam.service";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";

const withSpecializationsAndExams = (
    Component,
    loaderSize = "big"
) => {
    return props => {
        const [exams, setExams] = useState(null);
        const [specializations, setSpecializations] = useState(null);

        useEffect(() => {
            const fetchSpecializations = async () => {
                const result = await getAllSpecializations();
                setSpecializations(result);
            }

            const fetchExams = async () => {
                const result = await getAllExams();
                setExams(result);
            }

            fetchSpecializations();
            fetchExams();
        }, []);

        return (
            <LoaderBoundary size={loaderSize} condition={specializations == null || exams == null}>
                <Component {...props} specializations={specializations} exams={exams} />
            </LoaderBoundary>
        );
    };
}

export default withSpecializationsAndExams;
