#version 130

uniform mat4 invViewMat;
uniform mat4 invProjMat;
uniform vec3 pos;
uniform float duration;
uniform sampler2D depthTex;

uniform float maxRadiuss[300];
uniform float radiuss[300];
uniform float centerX[300];
uniform float centerY[300];
uniform float centerZ[300];

in vec2 texCoord;

const float sharpness = 10;
const vec4 outerColor = vec4(0.8, 1.0, 0.9, 1.0);
const vec4 midColor = vec4(0.4, 0.5, 0.7, 1.0);
const vec4 innerColor = vec4(0.1, 0.4, 0.9, 1.0);
const vec4 scanlineColor = vec4(0.6, 1.0, 0.2, 1.0);

float scanlines() {
    return sin(gl_FragCoord.y)*0.5+0.5;
}

vec3 worldpos(float depth) {
    float z = depth * 2.0 - 1.0;
    vec4 clipSpacePosition = vec4(texCoord * 2.0 - 1.0, z, 1.0);
    vec4 viewSpacePosition = invProjMat * clipSpacePosition;
    viewSpacePosition /= viewSpacePosition.w;
    vec4 worldSpacePosition = invViewMat * viewSpacePosition;

    return pos + worldSpacePosition.xyz;
}

void main() {
    float depth = texture2D(depthTex, texCoord).r;
    vec3 pos = worldpos(depth);

    float invs=0;
    float whiteInv=0;

    for (int i = 0; i < 300; i++){
        float dist = distance(pos, vec3(centerX[i], centerY[i], centerZ[i]));
        float ivc=radiuss[i]/maxRadiuss[i];
        float maxDist=radiuss[i] - min(maxRadiuss[i], 400)/125;
        if (dist < radiuss[i]  && dist > maxDist && depth < 1&&ivc>0) {
            invs =  1;
            float sa=radiuss[i]-maxDist;
            float cs=dist-maxDist;
            float cWI=min(max((cs/sa)*1.1, 0.2), 1);
            //   if (whiteInv > cWI){
            whiteInv=cWI;
            //   }
        }
    }

    gl_FragColor = vec4(whiteInv, whiteInv, whiteInv, invs);
}