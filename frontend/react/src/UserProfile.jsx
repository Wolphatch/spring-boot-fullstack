const UserProfile = ({name, age, gender, imageNumber, ...props})=>{
    gender = gender === "male"? "men" : "women"
    return (
            <img 
                src = {`https://randomuser.me/api/portraits/${gender}/${imageNumber}.jpg`}
            ></img>
    )
}

export default UserProfile