#VS
#version 150 core

in vec3 inPosition;

out vec4 worldPosition;
out vec3 localPosition;

uniform mat4 modelMatrix;
uniform mat4 mvp;

uniform sampler2D height;

uniform float size;

void main() {
	worldPosition = modelMatrix * vec4(inPosition, 1);
	localPosition = inPosition;

	vec2 texCoords = (worldPosition.xz / size + 1) / 2;
	worldPosition.y = texture(height, texCoords).r;

	gl_Position = mvp * vec4(inPosition.x, worldPosition.y, inPosition.z, 1);
}
#VS

#FS
#include <FS:mousePick-basic.glsl>
#FS
