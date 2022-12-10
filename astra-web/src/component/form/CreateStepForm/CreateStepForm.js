import {useState} from "react";
import { create } from "../../../service/step.service";
import Button from "../../Button/Button";
import Input from "../../Input/Input";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import Spacer from "../../Spacer/Spacer";

const CreateStepForm = ({
    onSuccess = () => {},
    onFailure = () => {}
}) => {
    const [title, setTitle] = useState("");
    const [loading, setLoading] = useState(false);

    const createStep = async () => {
        setLoading(true);
        const data = await create({
            title
        });

        onSuccess(data);
    }

    return (
        <div className="s-vflex-center modal-content">
            <Input withLabel={false} placeholder="Назва КРОКу" value={title}
                   onChange={event => setTitle(event.target.value)}/>
            <Spacer height={20}/>
            <div className="s-hflex-center">
                <LoaderBoundary condition={loading} size="small">
                    <Button isFilled={true} onClick={() => createStep()}>
                        Підтвердити
                    </Button>
                </LoaderBoundary>
            </div>
        </div>
    );
}

export default CreateStepForm;
