#VS
#version 150 core

in vec3 inPosition;
in vec2 inTexCoords;
in vec3 inNormal;

out vec2 texCoords;
out vec3 normal;
out vec4 position;

uniform mat4 modelMatrix;
uniform mat4 mvp;

void main() {
	position = modelMatrix * vec4(inPosition, 1);
	gl_Position = mvp * vec4(inPosition, 1);
	texCoords = inTexCoords;
	normal = (modelMatrix * vec4(inNormal, 0)).xyz;
}
#VS

#FS
#version 150 core

in vec2 texCoords;
in vec3 normal;
in vec4 position;

in vec3 cameraDirection;

out vec4 outColor;
out vec4 outPosition;
out vec4 outNormal;
out vec4 outSpecular;

uniform sampler2D diffuse;
uniform float reflectivity;
uniform float shineDamper;
uniform float lightingAmount;

void main() {
	outColor = texture(diffuse, texCoords);
	outPosition = position;
	outNormal = vec4(normalize(normal), 1);
	outSpecular = vec4(reflectivity, shineDamper, lightingAmount, 1);
}
#FS
