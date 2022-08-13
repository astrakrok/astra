import {useParams} from "react-router-dom";
import "./EditTestingPage.css";

const EditTestingPage = () => {
    const {id} = useParams();

    return (
        <div className="container">
            <div className="row">
                {id}
            </div>
        </div>
    );
}

export default EditTestingPage;
