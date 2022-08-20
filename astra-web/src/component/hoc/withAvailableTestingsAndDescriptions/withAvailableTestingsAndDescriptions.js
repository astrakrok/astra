import {useEffect, useState} from "react";
import {getAvailableTestings, getDescriptions} from "../../../service/testing.service";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";

const withAvailableTestingsAndDescriptions = (
    Component,
    loaderSize = "big"
) => {
    return props => {
        const [testings, setTestings] = useState(null);
        const [descriptions, setDescriptions] = useState(null);

        useEffect(() => {
            const fetchTestings = async () => {
                const result = await getAvailableTestings();
                setTestings(result);
            }

            const fetchDescriptions = async () => {
                const result = await getDescriptions();
                setDescriptions(result);
            }

            fetchTestings();
            fetchDescriptions();
        }, []);

        const getExams = () => {
            if (testings == null) {
                return;
            }
            const map = testings.reduce((previous, current) => {
                previous[current.exam.id] = current.exam;
                return previous;
            }, {});
            return Object.values(map);
        }

        const onExamSelected = examId => testings.filter(item => item.exam.id === examId).map(item => item.specialization);

        const onSelected = (examId, specializationId) => {
            const testing = testings.find(item => item.exam.id === examId && item.specialization.id === specializationId)
            return testing ? testing.id : null;
        };

        return (
            <LoaderBoundary size={loaderSize} condition={testings == null || descriptions == null}
                            className="s-hflex-center">
                <Component {...props} exams={getExams()} descriptions={descriptions} onExamSelected={onExamSelected}
                           onSelected={onSelected}/>
            </LoaderBoundary>
        );
    };
}

export default withAvailableTestingsAndDescriptions;
