const UserProfile = ({name, age, gender, imageNumber, ...props})=>{
    gender = gender === "male"? "men" : "women"
    return (
        <div>
            <h1>{name}</h1>
            <p>{age}</p>
            <img 
                src = {`https://randomuser.me/api/portraits/${gender}/${imageNumber}.jpg`}
            ></img>
            {props.children}
        </div>
    )
}

export default UserProfile