from flask import Flask,request,jsonify
import numpy as np
import pickle

model = pickle.load(open('model.pkl','rb'))
normalization = pickle.load(open('normalize.pkl','rb'))

app = Flask(__name__)

@app.route('/')
def index():
    return "Hello world"

@app.route('/predict',methods=['POST'])
def predict():
    Clump_Thickness = request.form.get('Clump_Thickness')
    Uniformity_Size = request.form.get('Uniformity_Size')
    Uniformity_Shape = request.form.get('Uniformity_Shape')
    Marginal_Adhesion = request.form.get('Marginal_Adhesion')
    Epithelial_Cell_Size = request.form.get('Epithelial_Cell_Size')
    Bare_Nuclei = request.form.get('Bare_Nuclei')
    Bland_Chromatin = request.form.get('Bland_Chromatin')
    Normal_Nucleoli = request.form.get('Normal_Nucleoli')
    Mitoses = request.form.get('Mitoses')
     


    input_query = np.array([Clump_Thickness,Uniformity_Size,Uniformity_Shape,Marginal_Adhesion,Epithelial_Cell_Size,Bare_Nuclei,Bland_Chromatin,Normal_Nucleoli,Mitoses],dtype=float)
    normal_query = np.c_[np.ones((1,1)),np.divide(np.subtract(input_query,normalization['mean']),normalization['std dev']).reshape(1,-1)]
    result = "Normal" if (model.predict(normal_query)[0]==0) else "Cancer"
    


    return jsonify({'Health Status':result})

if __name__ == '__main__':
    app.run(debug=True)
