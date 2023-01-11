import {useEffect, useState} from "react";
import {getSubjectsDetailsPage} from "../../../service/subject.service";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";

const withSubjectsDetails = (
    Component,
    loaderSize = "big",
    pageSize = 200,
    pageNumber = 0
) => {
    return props => {
        const [subjectsDetails, setSubjectsDetails] = useState(null);

        useEffect(() => {
            const fetchSubjectsDetails = async () => {
                const result = await getSubjectsDetailsPage({}, {
                    pageNumber: pageNumber,
                    pageSize: pageSize
                });
                setSubjectsDetails(result);
            }

            fetchSubjectsDetails();
        }, []);

        return (
            <LoaderBoundary size={loaderSize} condition={subjectsDetails == null}>
                <Component {...props} subjectsDetails={subjectsDetails && subjectsDetails.items} />
            </LoaderBoundary>
        );
    };
}

export default withSubjectsDetails;
