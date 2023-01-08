import {useEffect, useState} from "react";
import {getSubjectsDetailsPage} from "../../../service/subject.service";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";

const withSubjectsDetails = (
    Component,
    loaderSize = "big"
) => {
    return props => {
        const [subjectsDetails, setSubjectsDetails] = useState(null);

        useEffect(() => {
            const fetchSubjectsDetails = async () => {
                const result = await getSubjectsDetailsPage({}, {
                    pageNumber: 0,
                    pageSize: 200
                });
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
