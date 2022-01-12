import {useState} from "react";
import {useHistory} from "react-router-dom";
import {ArticleService} from "../../service/ArticleService";
import {UserService} from "../../service/UserService";
import {TokenService} from "../../service/TokenService";

export default function AddArticle() {
    const [selectedImg, setSelectedImg] = useState({})
    const [imgName, setImgName] = useState("")
    const divStyle = {height: 930, backgroundSize: 'cover'};
    const history = useHistory();

    function fileSelectedHandler(event) {
        let file = event.target.files[0]
        console.log("File to upload: " + file.name)
        if (file) {
            setImgName(file.name)
            const reader = new FileReader()
            reader.onload = handleReaderLoaded.bind(this)
            reader.readAsBinaryString(file)
        }
    }

    function handleReaderLoaded(readerEvent) {
        let binaryString = readerEvent.target.result
        setSelectedImg(btoa(binaryString)) // base64 string
    }

    async function addArticle(e) {
        const name = document.getElementById("name").value
        const description = document.getElementById("description").value
        const price = document.getElementById("price").value
        console.log(selectedImg)
        if (name !== "" && description !== "" && price !== "" && selectedImg !== null) {
            try {
                e.preventDefault();

                const formData = new FormData()
                const username = TokenService.decodeToken(TokenService.getToken()).sub
                const seller = (await UserService.getUser(username)).data
                formData.append('base64Image', selectedImg);
                formData.append('imgName', imgName);
                formData.append("name", name)
                formData.append("description", description)
                formData.append("price", price)
                formData.append("sellerId", seller.id)

                await ArticleService.addArticle(formData)
                alert("Article added successfully.")
                history.push("./browse")
            } catch (error) {
                console.error(`Error while adding new article: ${error}`);
            }
        } else {
            alert("Make sure to fill out all the fields !")
        }
    }

    return (
        <div style={divStyle}>
            <form className="form-size" method="POST">
                <h1>Add article</h1>

                <input className="form-control input-margin" id="name" type="text" placeholder="Name"/>
                <input className="form-control input-margin" id="description" type="text" placeholder="Description"/>
                <input className="form-control input-margin" id="price" type="number" placeholder="Price"/>
                <input accept="image/*" className="form-control input-margin"
                       id="picture" type="file" onChange={(e) => fileSelectedHandler(e)}/>
                <input className="form-control btn input-margin" onClick={addArticle} value="Submit"/>
            </form>
        </div>
    )
}