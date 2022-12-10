import {useEffect, useState} from "react";
import {getAllBySpecializationId} from "../../../service/exam.service";
import {getAll, getSpecializations} from "../../../service/step.service";
import {get, getDescriptions} from "../../../service/testing.service";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";

const withAvailableTestingsAndDescriptions = (
    Component,
    loaderSize = "big"
) => {
    return props => {
        const [steps, setSteps] = useState(null);
        const [specializations, setSpecializations] = useState(null);
        const [exams, setExams] = useState(null);
        const [descriptions, setDescriptions] = useState(null);

        const fetchSteps = async () => {
            const result = await getAll();
            setSteps(result);
        }

        const stepSelected = async stepId => {
            setExams(null);
            setSpecializations(null);
            const result = await getSpecializations(stepId);
            setSpecializations(result);
        }

        const specializationSelected = async specializationId => {
            setExams(null);
            const result = await getAllBySpecializationId(specializationId);
            setExams(result);
        }

        useEffect(() => {
            const fetchDescriptions = async () => {
                const result = await getDescriptions();
                setDescriptions(result);
            }

            fetchSteps();
            fetchDescriptions();
        }, []);

        const onSelected = async (examId, specializationId) => {
            return await get(examId, specializationId);
        };

        return (
            <LoaderBoundary size={loaderSize} condition={descriptions == null || steps == null}
                            className="s-hflex-center">
                <Component
                    {...props}
                    stepsList={steps}
                    onStepSelected={stepSelected}
                    specializationsList={specializations}
                    onSpecializationSelected={specializationSelected}
                    examsList={exams}
                    descriptions={descriptions}
                    onSelected={onSelected}/>
            </LoaderBoundary>
        );
    };
}

export default withAvailableTestingsAndDescriptions;
