import {useEffect, useState} from "react";
import {getSubjectsDetails} from "../../../service/subject.service";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";

const withSubjectsDetails = (
    Component,
    loaderSize = "big"
) => {
    return props => {
        const [subjectsDetails, setSubjectsDetails] = useState(null);

        useEffect(() => {
            const fetchSubjectsDetails = async () => {
                const result = await getSubjectsDetails();
                setSubjectsDetails(result);
            }

            fetchSubjectsDetails();
        }, []);

        return (
            <LoaderBoundary size={loaderSize} condition={subjectsDetails == null}>
                <Component {...props} subjectsDetails={subjectsDetails} />
            </LoaderBoundary>
        );
    };
}

export default withSubjectsDetails;
