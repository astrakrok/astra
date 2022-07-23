import {useEffect, useState} from "react";
import {getAll} from "../../../service/specialization.service";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";

const withSpecializations = (
    Component,
    loaderSize = "big"
) => {
    return props => {
        const [specializations, setSpecializations] = useState(null);

        useEffect(() => {
            const fetchSpecializations = async () => {
                const result = await getAll();
                setSpecializations(result);
            }

            fetchSpecializations();
        }, []);

        return (
            <LoaderBoundary size={loaderSize} condition={specializations == null}>
                <Component {...props} specializations={specializations} />
            </LoaderBoundary>
        );
    };
}

export default withSpecializations;
